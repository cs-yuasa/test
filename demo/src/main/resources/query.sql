SELECT g.id, g.message, u.name 
FROM greetings g
JOIN users u ON g.user_id = u.id;