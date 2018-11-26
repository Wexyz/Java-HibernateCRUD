<?php

function notification(){
  if(isset($_SESSION['notif'])){
    echo("<p>".htmlentities($_SESSION['notif'])."</p>\n");
    unset($_SESSION['notif']);
  }
}

function isCodeSet($pdo){
  $stmt = $pdo->prepare('SELECT invite_code FROM invite WHERE invite_code = :ic');
  $stmt->execute(array(':ic' => $_POST['invite_code']));
  $row = $stmt->fetch(PDO::FETCH_ASSOC);

  if($row !== false) return true;
  else return false;
}

function codeChecker($pdo){
  if(isset($_REQUEST['code'])){
    $stmt = $pdo->prepare('SELECT invite_code FROM invite WHERE invite_code = :ic');
    $stmt->execute(array(':ic' => $_REQUEST['code']));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);

    if($row === false) {
      $_SESSION['notif'] = "INVALID CODE: ".htmlentities($_REQUEST['code']);
      header('Location: index.php');
      return;
    }

  }
}

function getStatus($pdo, $code){

  $stmt = $pdo->prepare('SELECT status FROM invite WHERE invite_code = :ic');
  $stmt->execute(array(':ic' => $code));
  $row = $stmt->fetch(PDO::FETCH_ASSOC);

  return htmlentities($row['status']);

}

?>
