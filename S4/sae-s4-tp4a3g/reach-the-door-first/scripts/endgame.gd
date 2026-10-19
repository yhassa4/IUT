extends Control

func _ready():
	display_results()

func display_results():
	var scores = TeamManager.team_scores
	
	# 1. On trie les équipes de celle qui a le plus de points à celle qui en a le moins
	var sorted_teams = scores.keys()
	sorted_teams.sort_custom(func(a, b): return scores[a] > scores[b])
	
	# 2. On affiche le résultat
	for i in range(sorted_teams.size()):
		var team = sorted_teams[i]
		var points = scores[team]
		
		var label = Label.new()
		
		# Gestion du "1er", "2ème", "3ème"
		var rank_text = ""
		if i == 0:
			rank_text = "1er"
		else:
			rank_text = str(i + 1) + "ème"
			
		# capitalize() met une majuscule (bleu -> Bleu)
		label.text = rank_text + " - Équipe " + team.capitalize() + " : " + str(points) + " points"
		
		# On met en valeur le gagnant
		if i == 0:
			label.add_theme_color_override("font_color", Color.GOLD)
			# Message spécial si personne n'a gagné de points
			if points == 0:
				label.text = "Égalité ! Personne n'a marqué de points..."
				label.add_theme_color_override("font_color", Color.GRAY)
				 
		# Couleurs pour les perdants (ou si tout le monde est à zéro)
		elif team == "bleu":
			label.add_theme_color_override("font_color", Color.DODGER_BLUE)
		elif team == "rouge":
			label.add_theme_color_override("font_color", Color.CRIMSON)
		elif team == "vert":
			label.add_theme_color_override("font_color", Color.LIME_GREEN) 
			
		%Leaderboard.add_child(label)

func _on_bouton_menu_pressed() -> void:
	TeamManager.reset_scores()
	SceneManager.reset_progress()
	get_tree().change_scene_to_file("res://scenes/ui/Lobby.tscn")
