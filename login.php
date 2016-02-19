<?php
	
	require "init.php";
	
	$student_id = $_POST["login_id"];
	$student_password = $_POST["login_password"];
	
	$sql_query = "SELECT * FROM student_info WHERE student_id LIKE '$student_id' AND student_password LIKE '$student_password';";

	$result = mysqli_query($con, $sql_query);
	$rows = array();
	
	if (mysqli_num_rows($result) == 1){
		$rows['result'] = "1";
		$row = mysqli_fetch_assoc($result);
		
		$rows['response'][] = $row;
		
		echo json_encode($rows);
	}else{
		$rows['result'] = "0";
		
		echo json_encode($rows);
	}
	
?>