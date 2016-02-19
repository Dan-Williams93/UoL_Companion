<?php
	require "init.php";
	
	$course = $_POST["course"];
	$course_year = $_POST["course_year"];
	
	$response = array();
	
	$sql_query = "SELECT * FROM dates WHERE course LIKE '$course' AND year LIKE '$course_year';";
	
	$result = mysqli_query($con, $sql_query);
	
	if (mysqli_num_rows($result) > 0){
		$response["status"] = "1";
		
		while ($row = mysqli_fetch_assoc($result)){
			$response["result"][] = $row;
		}
		
		
		echo json_encode($response);
		
	}else{
		$response["status"] = "0";
		echo json_encode($response);
	}
?>