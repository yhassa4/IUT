extends Node

# On n'instancie plus le joueur ici, c'est TeamManager qui s'en occupe
var server = TCPServer.new()
var port = 8080
var connected_players = {} 

func _ready():
	var err = server.listen(port)
	if err != OK:
		print("ERREUR : Impossible de lancer le serveur")
	else:
		print("SERVEUR PRÊT : En attente des joueurs (HTML)...")

func _process(_delta):
	# 1. Gérer les arrivées des pages HTML
	if server.is_connection_available():
		var conn = server.take_connection()
		var new_peer = WebSocketPeer.new()
		new_peer.accept_stream(conn)
		
		# On utilise le hash unique du socket comme ID de joueur
		var player_id = new_peer.get_instance_id()
		
		# On enregistre le joueur dans le TeamManager (qui va le mettre dans une équipe)
		TeamManager.register_new_player(player_id)
		
		# On stocke le socket avec son ID
		connected_players[new_peer] = player_id
		print("LOG : Nouvelle page HTML connectée (ID: ", player_id, ")")

	# 2. Gérer les messages
	for client in connected_players.keys():
		client.poll()
		var state = client.get_ready_state()
		
		if state == WebSocketPeer.STATE_OPEN:
			while client.get_available_packet_count() > 0:
				var msg = client.get_packet().get_string_from_utf8()
				var player_id = connected_players[client]
				
				# On trouve l'équipe dynamiquement
				var team = ""
				for t_name in TeamManager.teams.keys():
					if player_id in TeamManager.teams[t_name]:
						team = t_name
						break # On a trouvé l'équipe, on arrête de chercher
				
				# Si le joueur a bien une équipe, on transmet l'ordre
				if team != "":
					var p = TeamManager.active_characters[team]
					
					if p and is_instance_valid(p):
						if msg == "GAUCHE": 
							p.set_move_direction(player_id, -1.0)
						elif msg == "DROITE": 
							p.set_move_direction(player_id, 1.0)
						elif msg == "SAUTER": 
							p.jump(player_id)
						elif msg == "RELACHE_SAUT":
							p.release_jump(player_id)
						elif msg == "STOP":   
							p.set_move_direction(player_id, 0.0)

		elif state == WebSocketPeer.STATE_CLOSED or state == WebSocketPeer.STATE_CLOSING:
			var player_id = connected_players[client]
			connected_players.erase(client)
			print("LOG : Une page HTML a été fermée.")

func _exit_tree():
	server.stop()
