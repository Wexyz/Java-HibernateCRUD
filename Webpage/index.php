<?php
  require_once 'pdo.php';
  require_once 'util.php';
  session_start();
?>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>RSVP WEB</title>
    <script src="https://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
  </head>
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <body>
    <h1>RSVP</h1>
    <?php
      notification();
      codeChecker($pdo);

      //IF CODE IS SUBMITTED
      if( isset($_POST['code_submit']) ){
        //IF code input is blank, print error, redirect to index
        if( strlen($_POST['invite_code']) < 1){
          $_SESSION['notif'] = "PLEASE ENTER CODE";
          header('Location: index.php');
          return;
        } else {
          if(!isCodeSet($pdo)) {
            $_SESSION['notif'] = "CODE NOT FOUND";
            header('Location: index.php');
            return;

          } else {
            header('Location: index.php?code='.$_POST['invite_code']);
            return;
          }

        }
      }

      //IF UPDATE IS SUBMITTED
      if( isset($_POST['update']) ){
        $stmt = $pdo->prepare('UPDATE invite SET status = :stat WHERE invite_code = :ic');
        $stmt->execute(array(':ic' => $_REQUEST['code'],
                              ':stat' => $_POST['response']));

        $_SESSION['notif'] = "Response Updated";
        header('Location: index.php');
        return;
      }
    ?>

    <!-- SHOWS DIV AFTER SUBMISSION OF VALID CODE -->
    <?php
      if(isset($_REQUEST['code'])){
      }
    ?>

    <div id="response_div" class="w3-container w3-teal"
      style="display : <?php if(isset($_REQUEST['code'])) { echo 'block';} else echo 'none'; ?>">

      <?php
        $stmt=$pdo->prepare('SELECT name FROM invite WHERE invite_code = :ic');
        $stmt->execute(array(':ic'=>$_REQUEST['code']));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        echo('<h3>Hello, '.htmlentities($row['name']).'</h3>');
      ?>
      <h3>Please Verify Response</h3>
      <form method="post" name="responseSelection">
        <input type="radio" name="response" value="0"> Undecided <br>
        <input type="radio" name="response" value="1"> Attending <br>
        <input type="radio" name="response" value="2"> Not Attending <br>

        <input type="submit" value="UPDATE" name="update">
      </form>
    </div>

    <div class="w3-container w3-red">
      <form method="post">
        <p>ENTER INVITE CODE</p>
        <p><input type="text" name="invite_code" id="invite_code"></p>

        <input type="submit" value="SUBMIT" name="code_submit" id="code_submit">
      </form>
    </div>

    <!-- SETS DEFAULT RADIO BOX SELECTION ACCORDING TO RETRIEVED DATA -->
    <script>
      $(document).ready(function(){
        document.responseSelection.response[<?php
          $status = getStatus($pdo, $_REQUEST['code']);
          echo $status;
        ?>].checked=true;

      });
    </script>
  </body>
</html>
