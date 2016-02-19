<?php
	
	require "init.php";
	
	$postCourse = $_POST["post_course"];
	
	$response = array();
	
	$sql_query = "SELECT * FROM posted_status WHERE course LIKE '$postCourse';";
	
	$result = mysqli_query($con, $sql_query);
	
	if (mysqli_num_rows($result) > 0){
		$response["status"] = "1";
		
		while($row = mysqli_fetch_assoc($result)){
			$response["posts"][] = $row;
		}
		
		echo json_encode($response);
	}else{
		$response["status"] = "0";
		echo json_encode($response);
	}

?>