<?php

	require "init.php";
	
	$postID = $_POST["post_id"];
	
	$response = array();
	
	$sql_query = "SELECT * FROM post_comments WHERE post_id LIKE '$postID';";
	
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