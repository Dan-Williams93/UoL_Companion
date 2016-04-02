<?php
	
	#INCLUDED THE CONNECTION CONSTRUCTION PHP FILE
	require "init.php";
	
	#RETRIEVE VALUES FROM POST REQUEST SENT FROM APPLICATION
	$postersName = $_POST["poster_name"];
	$post = $_POST["post"];
	$postDate = $_POST["post_date"];
	$postCourse = $_POST["post_course"];
	
	$response = 0;
	
	#CONSTRUCT QUERY
	$sql_query = "INSERT INTO posted_status (poster_name, status, post_date, course) VALUES ('$postersName', '$post', '$postDate', '$postCourse');";
	
	#EXECUTE AND CHECK SUCCESS OF INSERT QUERY
	if (mysqli_query($con, $sql_query)){
		$response = 1;
		
		#RETURN RESPONSE TO APPLICATION
		echo $response;
	}else{
		
		#SET AND RETURN FAILED RESPONSE TO APPLICAITON
		$response = 0;
		echo $response;
	}

?>