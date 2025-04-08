package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.GreetingUserDTO;
import com.example.demo.model.User;
import com.example.demo.service.GreetingService;

/**
 * GreetingControllerの単体テストクラス。
 */
@WebMvcTest(GreetingController.class)
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingService;

    /**
     * 新規作成処理の正常系テスト。
     * 名前とメッセージを送信し、リダイレクトと成功メッセージが返ることを検証します。
     */
    @Test
    void 新規作成テスト_正常系() throws Exception {
        doNothing().when(greetingService).addNameAndMessage("テストユーザー", "こんにちは");

        mockMvc.perform(post("/create")
                .param("name", "テストユーザー")
                .param("message", "こんにちは"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMessage", "作成が完了しました！"));

        verify(greetingService, times(1)).addNameAndMessage("テストユーザー", "こんにちは");
    }

    /**
     * データが存在する場合のトップページ表示テスト。
     * モデル属性にデータが設定されていることを検証します。
     */
    @Test
    void データあり表示テスト_正常系() throws Exception {
        when(greetingService.getAllGreetingsWithUsers()).thenReturn(List.of(new GreetingUserDTO(1L, "こんにちは", "メモ", "テストユーザー")));
        when(greetingService.getAllUsers()).thenReturn(List.of(new User(1L, "テストユーザー")));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users", "greetings"))
                .andExpect(model().attribute("greetings", List.of(new GreetingUserDTO(1L, "こんにちは", "メモ", "テストユーザー"))));
    }

    /**
     * データが存在しない場合のトップページ表示テスト。
     * モデル属性が空であってもページが正しく表示されることを確認します。
     */
    @Test
    void データなし表示テスト_正常系() throws Exception {
        when(greetingService.getAllGreetingsWithUsers()).thenReturn(List.<GreetingUserDTO>of());
        when(greetingService.getAllUsers()).thenReturn(List.<User>of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users", "greetings"))
                .andExpect(model().attribute("greetings", List.of()));
    }

    /**
     * 更新処理の正常系テスト。
     * 名前とメッセージとメモを送信し、リダイレクトと成功メッセージが返ることを検証します。
     */
    @Test
    void データ更新テスト_正常系() throws Exception {
        doNothing().when(greetingService).updateGreetingAndUser(1L, "更新後ユーザー", "更新後メッセージ", "更新後メモ");

        mockMvc.perform(post("/update/1")
                .param("name", "更新後ユーザー")
                .param("message", "更新後メッセージ")
                .param("memo", "更新後メモ"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMessage", "更新が完了しました！"));

        verify(greetingService, times(1)).updateGreetingAndUser(1L, "更新後ユーザー", "更新後メッセージ", "更新後メモ");
    }

    /**
     * 削除処理の正常系テスト。
     * リダイレクトと成功メッセージが返ることを検証します。
     */
    @Test
    void データ削除テスト_正常系() throws Exception {
        doNothing().when(greetingService).deleteGreeting(1L);

        mockMvc.perform(post("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMessage", "削除が完了しました！"));

        verify(greetingService, times(1)).deleteGreeting(1L);
    }
}
