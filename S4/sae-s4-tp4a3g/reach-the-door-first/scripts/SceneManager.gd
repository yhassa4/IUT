extends Node

# Liste des niveaux dans l'odre
const LEVELS = [
	"res://scenes/niveaux/Niveau1.tscn",
	"res://scenes/niveaux/Niveau2.tscn",
	"res://scenes/niveaux/Niveau3.tscn",
	"res://scenes/ui/EndGame.tscn"
]

var current_level_index = 0
 
func next_level():
	if current_level_index < LEVELS.size():
		get_tree().call_deferred("change_scene_to_file", LEVELS[current_level_index])
	else:
		print("Jeu terminé : retour au lobby")
		get_tree().call_deferred("change_scene_to_file", "res://scenes/ui/Lobby.tscn")
	
	current_level_index += 1
	
	# On attend que Godot finisse de charger les nouveaux nœuds
	await get_tree().process_frame 
	# On attend une deuxième frame pour être SÛR que le _ready() du niveau est fini
	await get_tree().process_frame
	
	var players = get_tree().get_nodes_in_group("players")
	
	for player in players:
		if (player.has_method("moveToSpawnPoint")):
			player.moveToSpawnPoint()
		else:
			push_error("Erreur : joueur pas de méthode moveToSpawnPoint")
	

func reset_progress():
	current_level_index = 0

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass # Replace with function body.

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass
