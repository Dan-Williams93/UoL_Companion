<?php

	require "init.php";
	
	$postID = $_POST["post_id"]; 
	$commenterName = $_POST["commenter_name"]; 
	$commentDate =  $_POST["comment_date"]; 
	$comment = $_POST["comment"];
	$numComments = $_POST["numComments"];

	//$postID = "1"; 
	//$commenterName = "commenter_name"; 
	//$commentDate =  "2016-01-01"; 
	//$comment = "comment";
	//$numComments = "0";	

	$response = 0;
	
	$newNumComments = $numComments+1;

	$sql_query = "INSERT INTO post_comments (post_id, commenter_name, comment_date, comment) VALUES ('$postID','$commenterName','$commentDate','$comment');";
	$sql_update_query = "UPDATE posted_status SET num_comments='$newNumComments' WHERE status_id=$postID";
	
	if(mysqli_query($con, $sql_query)){
		$response = 1;
		mysqli_query($con, $sql_update_query);
		echo $response;
	}else{
		$response = 0;
		echo $response;
	}
	

?>