extends CanvasLayer

var liste_bleue
var liste_rouge
var liste_verte

func _ready():
	liste_bleue = find_child("BlueTeam", true, false)
	liste_rouge = find_child("RedTeam", true, false)
	liste_verte = find_child("GreenTeam", true, false)

	if TeamManager:
		TeamManager.teams_updated.connect(update_ui)
		update_ui()
	else:
		print("ERREUR : TeamManager introuvable dans GameUI")

func update_ui():
	# On s'assure que les 3 listes existent
	if not liste_bleue or not liste_rouge or not liste_verte: return

	# Nettoyage
	for c in liste_bleue.get_children(): c.queue_free()
	for c in liste_rouge.get_children(): c.queue_free()
	for c in liste_verte.get_children(): c.queue_free()

	# Titres
	var blueTeamLabel = Label.new()
	blueTeamLabel.text = "Blue Team"
	blueTeamLabel.add_theme_color_override("font_color", Color.BLUE)
	liste_bleue.add_child(blueTeamLabel)
	
	var redTeamLabel = Label.new()
	redTeamLabel.text = "Red Team"
	redTeamLabel.add_theme_color_override("font_color", Color.RED)
	liste_rouge.add_child(redTeamLabel)

	var greenTeamLabel = Label.new()
	greenTeamLabel.text = "Green Team"
	greenTeamLabel.add_theme_color_override("font_color", Color.GREEN)
	liste_verte.add_child(greenTeamLabel)

	# --- REMPLISSAGE DES ÉQUIPES ---
	for i in range(TeamManager.teams["bleu"].size()):
		creer_label(i, TeamManager.teams["bleu"][i], "bleu", liste_bleue)

	for i in range(TeamManager.teams["rouge"].size()):
		creer_label(i, TeamManager.teams["rouge"][i], "rouge", liste_rouge)

	for i in range(TeamManager.teams["vert"].size()): # <-- NOUVEAU
		creer_label(i, TeamManager.teams["vert"][i], "vert", liste_verte)


func creer_label(index, player_id, equipe, container):
	var l = Label.new()
	
	# Texte plus compact
	l.text = "J" + str(index) # "J0", "J1" au lieu de "Joueur 0" pour gagner de la place
	
	if TeamManager.active_characters.has(equipe):
		var perso = TeamManager.active_characters[equipe]
		if perso and perso.current_controller_id == player_id:
			l.text += " ★" # Un petit symbole au lieu de [CHEF]
			l.add_theme_color_override("font_color", Color.YELLOW)
	
	# On réduit la taille
	l.add_theme_font_size_override("font_size", 12) 
	
	container.add_child(l)
