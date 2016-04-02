<?php
	
	#INCLUDED THE CONNECTION CONSTRUCTION PHP FILE
	require "init.php";
	
	#RETRIEVE VALUES FROM POST REQUEST SENT FROM APPLICATION
	$postCourse = $_POST["post_course"];
	
	$response = array();
	
	#CONSTRUCT QUERY
	$sql_query = "SELECT * FROM posted_status WHERE course LIKE '$postCourse';";
	
	#EXECUTE SQL STATEMENT ON CONNECTION ESTABLISHED
	$result = mysqli_query($con, $sql_query);
	
	if (mysqli_num_rows($result) > 0){
		
		#ADD A STATUS IDENTIFIER AND VALUE TO THE START OF THE RESPONSE JSON
		$response["status"] = "1";
		
		#ADD EACH ROW OF THE RESULT TO A NEW ARRAY WITHIN THE RESPONSE
		while($row = mysqli_fetch_assoc($result)){
			$response["posts"][] = $row;
		}
		
		#ENCODE IN JSON AND RETURN TO APPLICATION
		echo json_encode($response);
	}else{
		
		#CREATE AND RETURN THE FAILED JSON TO APPLICATION
		$response["status"] = "0";
		echo json_encode($response);
	}

?>