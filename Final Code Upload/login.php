<?php
	
	#INCLUDED THE CONNECTION CONSTRUCTION PHP FILE
	require "init.php";
	
	#RETRIEVE VALUES FROM POST REQUEST SENT FROM APPLICATION
	$student_id = $_POST["login_id"];
	$student_password = $_POST["login_password"];
	
	#CONSTRUCT QUERY
	$sql_query = "SELECT * FROM student_info WHERE student_id LIKE '$student_id' AND student_password LIKE '$student_password';";

	#EXECUTE SQL STATEMENT ON CONNECTION ESTABLISHED
	$result = mysqli_query($con, $sql_query);
	$rows = array();
	
	if (mysqli_num_rows($result) == 1){
		
		#ADD A STATUS IDENTIFIER AND VALUE TO THE START OF THE RESPONSE JSON
		$rows['result'] = "1";
		
		#RETRIEVE ROW OF USER INFORMATION
		$row = mysqli_fetch_assoc($result);
		
		#ADD DATA TO THE RESPONSE
		$rows['response'][] = $row;
		
		#ENCODE IN JSON AND RETURN TO APPLICATION
		echo json_encode($rows);
	}else{
		
		#CREATE AND RETURN THE FAILED JSON TO APPLICATION 
		$rows['result'] = "0";
		echo json_encode($rows);
	}
	
?>