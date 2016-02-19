<?php
	
	require "init.php";
	
	$postersName = $_POST["poster_name"];
	$post = $_POST["post"];
	$postDate = $_POST["post_date"];
	$postCourse = $_POST["post_course"];
	
	$response = 0;
	
	$sql_query = "INSERT INTO posted_status (poster_name, status, post_date, course) VALUES ('$postersName', '$post', '$postDate', '$postCourse');";
	
	if (mysqli_query($con, $sql_query)){
		$response = 1;
				
		echo $response;
	}else{
		$response = 0;
		echo $response;
	}

?>