package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.User;
import com.example.demo.service.GreetingService;

/**
 * 挨拶とユーザーに関する操作を処理するコントローラークラス。
 * フォームからの入力や画面遷移を制御します。
 */
@Controller
@RequestMapping("/")
public class GreetingController {
	
	private final GreetingService greetingService;
	
	/**
	 * GreetingControllerのコンストラクタ。
	 * 
	 * @param greetingService 挨拶とユーザー情報の操作を行うサービスクラス。
	 */
	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	/**
     * フォームから送信されたデータを受け取り、挨拶およびユーザー情報を保存し、
     * 成功メッセージとともにリダイレクトします。
     * 
     * @param name ユーザー名
     * @param message 挨拶メッセージ
     * @param redirectAttributes フラッシュ属性を追加するためのオブジェクト
     * @param request HTTPリクエストオブジェクト
     * @return リダイレクト先のパス
     */
	@PostMapping("/create")
	public String createGreetingAndUser(
			@RequestParam String name, 
			@RequestParam String message, 
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		greetingService.addNameAndMessage(name, message);
		redirectAttributes.addFlashAttribute("successMessage", "作成が完了しました！");
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}
	
	/**
	 * ユーザー名と挨拶の一覧を取得し、ビューに渡して表示します。
	 * 
	 * @param model ビューに渡すデータを保持する Model オブジェクト
	 * @return 表示するテンプレートの名前
	 */
	@GetMapping
	public String getGreetingsWithUsers(Model model) {
		model.addAttribute("users", greetingService.getAllUsers());
		model.addAttribute("greetings", greetingService.getAllGreetingsWithUsers());
		return "index";
    }
	
	/**
	 * 指定されたIDに対応する挨拶およびユーザー情報を更新し、元のページへリダイレクトします。
	 * 
	 * @param id 更新対象のID
	 * @param name 更新後のユーザー名
	 * @param message 更新後の挨拶メッセージ
	 * @param memo 更新後のメモ
	 * @param redirectAttributes フラッシュ属性を追加するためのオブジェクト
	 * @param request HTTPリクエストオブジェクト
	 * @return リダイレクト先のパス
	 */
	@PostMapping("/update/{id}")
	public String updateMessageAndName(
			@PathVariable Long id, 
			@RequestParam String name, 
			@RequestParam String message, 
			@RequestParam String memo, 
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		greetingService.updateGreetingAndUser(id, name, message, memo);
		redirectAttributes.addFlashAttribute("successMessage", "更新が完了しました！");
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}
	
	/**
	 * 指定されたIDに対応する挨拶データを削除し、元のページへリダイレクトします。
	 * 
	 * @param id 削除対象のID
	 * @param redirectAttributes フラッシュ属性を追加するためのオブジェクト
	 * @param request HTTPリクエストオブジェクト（リファラ取得用）
	 * @return リダイレクト先のパス
	 */
	@PostMapping("/delete/{id}")
	public String deleteGreeting(
			@PathVariable Long id, 
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		greetingService.deleteGreeting(id);
		redirectAttributes.addFlashAttribute("successMessage", "削除が完了しました！");
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}
	
	/**
	 * クエリパラメータで受け取ったユーザー名をもとにユーザー一覧を取得します。
	 * 
	 * @param name ユーザー名
	 * @return 指定されたユーザー名に一致するユーザーのリスト
	 */
	@GetMapping("/api")
	@ResponseBody
	public List<User> searchUsersByName(@RequestParam String name) {
	    return greetingService.getUsersByName(name);
	}
	
	/**
	 * リクエストボディで受けったユーザー名をもとにユーザーを作成します。
	 * すでに同じユーザー名が存在する場合は作成しません。
	 * 
	 * @param request ユーザー情報を含むリクエストボディ
	 * @return ResponseEntity 成功・エラーメッセージを含むレスポンス
	 */
	@PostMapping("/api")
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestBody User request) {
		List<User> user = greetingService.getUsersByName(request.getName());
		if (user == null || user.isEmpty()) {
			greetingService.addUser(request.getName());
			return ResponseEntity.status(HttpStatus.OK)
                    .body("作成が完了しました。");
		} else {
			System.out.println(user);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("この名前はすでに使われています。");
		}
	}
}
