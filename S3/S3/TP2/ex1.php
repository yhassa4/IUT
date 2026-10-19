<?php 

echo "<h1>Ceci est la page générée par l'interprétation de ex1.php</h1>";

$today = getdate();

echo "<pre>";
print_r($today);
echo "</pre>";

$weekday = $today["weekday"];
$mday = $today["mday"];
$month = $today["month"];
$year = $today["year"];

$hours = $today["hours"];
$minutes = $today["minutes"];
$seconds = $today["seconds"];


echo "l'utilisateur thoang3 a demandé cette page le $weekday $mday $month $year à $hours h $minutes min $seconds s";

?>