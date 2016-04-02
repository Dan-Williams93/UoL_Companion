<?php

	#INITIATIE VARIABES WITH DATABASE INFORMATION
	$db_server = "localhost";
	$db_username = "root";
	$db_password = "";
	$db_name = "uol_companion";
	
	#CONSTRUCT A CONNECTION TO THE SPECIFIED DATABASE
	$con = mysqli_connect($db_server, $db_username, $db_password, $db_name);
	
	#CHECK SUCCESSFULNESS OF CONNECTION
	if ($con){
		//echo "Connection Made";
	}else{
		//echo "No Connection";
	}

?>