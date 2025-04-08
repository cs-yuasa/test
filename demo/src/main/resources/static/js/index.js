document.addEventListener("DOMContentLoaded", function() {
	const createButton = document.getElementById("create-button");
	const createName = document.getElementById("create-name");
	const createResult = document.getElementById("create-result");
	const searchButton = document.getElementById("search-button");
	const searchName = document.getElementById("search-name");
	const searchResult = document.getElementById("search-result");

	createButton.addEventListener("click", function() {
		const name = createName.value.trim();
		if (!name) {
			createResult.innerHTML = "<p style='color:red;'>名前を入力してください。</p>";
			return;
		}
        fetch("/api", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name: name })
        })
			.then(response => {
		        return response.text().then(message => {
		            if (response.status === 200) {
		                createResult.innerHTML = "<p style='color: #4CAF50;'>" + message + "</p>";
		            } else if (response.status === 400) {
		                createResult.innerHTML = "<p style='color:red;'>" + message + "</p>";
		      		}
				});
			})
	        .catch(error => {
				createResult.innerHTML = "<p style='color:red;'>エラーが発生しました。</p>";
				console.error("APIエラー:", error);
	        });
	});
	searchButton.addEventListener("click", function() {
		const name = searchName.value.trim();
		if (!name) {
			searchResult.innerHTML = "<p style='color:red;'>名前を入力してください。</p>";
			return;
		}

		fetch(`/api?name=${encodeURIComponent(name)}`, {
		    method: "GET",
		    headers: {
		        "Content-Type": "application/json"
		    }
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
