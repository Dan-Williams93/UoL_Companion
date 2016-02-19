<?php
	
	require "init.php";
	
	$search_criteria = $_POST["search_input"];
	$search_field = $_POST["search_field"];
	
	$response = array();
	
	$sql_query = "SELECT * FROM staff WHERE $search_field LIKE '%$search_criteria%';";
	
	$result = mysqli_query($con, $sql_query);
	
	if (mysqli_num_rows($result) > 0){
		$response["status"] = "1";
		
		while($row = mysqli_fetch_assoc($result)){
			$response["result"][] = $row;
		}
		
		echo json_encode($response);
	
	}else{
		$response["status"] = "0";
		echo json_encode($response);
	}
	
	
?>