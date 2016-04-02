<?php

	#INCLUDED THE CONNECTION CONSTRUCTION PHP FILE
	require "init.php";
	
	#RETRIEVE VALUES FROM POST REQUEST SENT FROM APPLICATION
	$postID = $_POST["post_id"]; 
	$commenterName = $_POST["commenter_name"]; 
	$commentDate =  $_POST["comment_date"]; 
	$comment = $_POST["comment"];
	$numComments = $_POST["numComments"];	

	$response = 0;
	
	#CALCULATE NEW NUMBER OF COMMENTS
	$newNumComments = $numComments+1;

	#CONSTRUCT QUERIES
	$sql_query = "INSERT INTO post_comments (post_id, commenter_name, comment_date, comment) VALUES ('$postID','$commenterName','$commentDate','$comment');";
	$sql_update_query = "UPDATE posted_status SET num_comments='$newNumComments' WHERE status_id=$postID";
	
	#EXECURE AND CHECK SUCCESS OF INSERT QUERY
	if(mysqli_query($con, $sql_query)){ 
		$response = 1;
		
		#EXECUTE UPDATE QUERY TO CHANEG NUMBER OF COMMENTS TO NEW VALUE 
		mysqli_query($con, $sql_update_query);
		
		#RETURN RESPONSE TO APPLICATION 
		echo $response;
	}else{
		
		#SET AND RETURN FAILED RESPONSE TO APPLICAITON
		$response = 0;
		echo $response;
	}
	

?>