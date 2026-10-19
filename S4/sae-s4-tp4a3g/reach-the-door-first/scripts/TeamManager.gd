extends Node

signal teams_updated
signal scores_updated

# À ajouter en haut de ton script principal
var team_colors = {
	"bleu": Color.CORNFLOWER_BLUE,
	"rouge": Color.INDIAN_RED,
	"vert": Color.LIME_GREEN
}

#1. Structure des données
var teams = {
	"bleu": [],
	"rouge": [],
	"vert": []
}

var active_player_index = {
	"bleu": 0,
	"rouge": 0,
	"vert": 0
}

var active_characters = {
	"bleu": null,
	"rouge": null,
	"vert": null
}

var team_scores = {
	"bleu": 0,
	"rouge": 0,
	"vert": 0
}

# 2. Assigner une équipe
func register_new_player(player_id: int):
	var team_assigned = ""
	var min_players = 999999
	
	# On définit l'ordre de priorité : Rouge d'abord !
	var priority_order = ["rouge", "bleu", "vert"]
	
	for t_name in priority_order:
		if teams[t_name].size() < min_players:
			min_players = teams[t_name].size()
			team_assigned = t_name
	
	teams[team_assigned].append(player_id)
	print("Page HTML ", player_id, " assignée à : ", team_assigned)
	
	if active_characters[team_assigned] == null:
		spawn_character_for_team(team_assigned)
		
	teams_updated.emit()

# 3. Créer le personnage
const PLAYER_SCENE = preload("res://scenes/player.tscn")

func spawn_character_for_team(team_name: String):
	var new_player = PLAYER_SCENE.instantiate()
	add_child(new_player)
	
	new_player.team_name = team_name
	var first_id = teams[team_name][0]
	new_player.current_controller_id = first_id
	

	# On vérifie si le joueur a une fonction pour changer sa couleur
	if new_player.has_method("apply_team_color"):
		new_player.apply_team_color(team_colors[team_name])

	
	active_characters[team_name] = new_player
	new_player.name = "Player_" + team_name

# 4. Rotation
# 4. LA ROTATION (Appelée par le perso quand il meurt)
func rotate_player(team_name: String):
	var team_list = teams[team_name]
	
	if team_list.size() > 1:
		# On passe au suivant dans la liste
		active_player_index[team_name] += 1
		
		# Si on arrive au bout, on revient au début
		if active_player_index[team_name] >= team_list.size():
			active_player_index[team_name] = 0
		
		# On récupère le nouvel ID
		var next_id = team_list[active_player_index[team_name]]
		
		# On met à jour le personnage physique
		if active_characters[team_name] != null:
			var perso = active_characters[team_name]
			perso.current_controller_id = next_id
			

			if perso.has_method("set_move_direction"):
				perso.set_move_direction(next_id, 0.0)
						
			print("ROTATION : C'est au tour du joueur ", next_id, " (Equipe ", team_name, ")")
			
		teams_updated.emit()
	else:
		print("Seul un joueur dans l'équipe ", team_name, ", pas de rotation nécessaire.")
		
# La fonction pour ajouter des points
func add_points(team_name: String, amount: int):
	if team_scores.has(team_name):
		team_scores[team_name] += amount
		scores_updated.emit()
		print("L'équipe ", team_name, " gagne ", amount, " points ! Total: ", team_scores[team_name])

# La fonction pour remettre les scores à zéro quand on retourne au lobby
func reset_scores():
	for team in team_scores.keys():
		team_scores[team] = 0
	scores_updated.emit()
	
