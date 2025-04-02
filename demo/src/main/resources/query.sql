SELECT g.id, g.message, g.memo, u.name 
FROM greetings g
JOIN users u ON g.user_id = u.id
ORDER BY g.id;