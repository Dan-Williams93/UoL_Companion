<?php

	$db_server = "localhost";
	$db_username = "root";
	$db_password = "";
	$db_name = "uol_companion";
	
	$con = mysqli_connect($db_server, $db_username, $db_password, $db_name);
	
	if ($con){
		//echo "Connection Made";
	}else{
		//echo "No Connection";
	}

?>