document.addEventListener("DOMContentLoaded", function() {
	const searchButton = document.getElementById("search-button");
	const searchName = document.getElementById("search-name");
	const searchResult = document.getElementById("search-result");

	searchButton.addEventListener("click", function() {
		const name = searchName.value.trim();
		if (!name) {
			searchResult.innerHTML = "<p style='color:red;'>名前を入力してください。</p>";
			return;
		}

		fetch("/api", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify({ name })
		})
			.then(response => response.json())
			.then(users => {
				if (users.length == 0) {
					searchResult.innerHTML = "<p>一致するユーザーが見つかりませんでした。</p>";
				} else {
					let html = "<ul>";
					users.forEach(user => {
						html += `<li>ID: ${user.id}, 名前: ${user.name}</li>`;
					});
					html += "</ul>";
					searchResult.innerHTML = html;
				}
			})
			.catch(error => {
				searchResult.innerHTML = "<p style='color:red;'>エラーが発生しました。</p>";
				console.error("APIエラー:", error);
			});
	});
});
