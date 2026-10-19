extends Node

@onready var player_scene = preload("res://scenes/player.tscn")

var server = TCPServer.new()
var port = 8080
var connected_players = {} 

func _ready():
	var err = server.listen(port)
	if err != OK:
		print("ERREUR : Impossible de lancer le serveur")
	else:
		print("SERVEUR PRÊT : En attente des joueurs...")

func _process(_delta):
	# 1. Gérer les arrivées
	if server.is_connection_available():
		var conn = server.take_connection()
		var new_peer = WebSocketPeer.new()
		new_peer.accept_stream(conn)
		
		# Création du joueur
		var new_player = player_scene.instantiate()
		get_tree().root.add_child(new_player)
		
		# On lie le socket au personnage
		connected_players[new_peer] = new_player
		print("LOG : Un nouveau joueur est entré.")

	# 2. Gérer les messages et les départs
	for client in connected_players.keys():
		client.poll()
		var state = client.get_ready_state()
		
		if state == WebSocketPeer.STATE_OPEN:
			while client.get_available_packet_count() > 0:
				var msg = client.get_packet().get_string_from_utf8()
				var p = connected_players[client]
				
				# DANS TON SCRIPT SERVEUR
				if msg == "GAUCHE": 
					p.move_direction = -1
				if msg == "DROITE": 
					p.move_direction = 1
				if msg == "SAUTER": 
					p.jump()
				if msg == "RELACHE_SAUT":
					p.release_jump() # Appelle la fonction qu'on a créée dans player.gd
				if msg == "STOP":   
					p.move_direction = 0

		elif state == WebSocketPeer.STATE_CLOSED or state == WebSocketPeer.STATE_CLOSING:
			var p = connected_players[client]
			if is_instance_valid(p):
				p.queue_free()
			connected_players.erase(client)
			print("LOG : Un joueur a quitté la partie.")

func _exit_tree():
	server.stop()
