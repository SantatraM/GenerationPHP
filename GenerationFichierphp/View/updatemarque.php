<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="<?php echo site_url("marqueController/updatemarque") ?>" method="post">
        <input name='idmarque' type='text' value='<?php echo $marque->idmarque; ?>' hidden>
        <input name='nommarque' type='text' value='<?php echo $marque->nommarque; ?>'>
        <input name='datesortie' type='date' value='<?php echo $marque->datesortie; ?>'>

        <input type="submit" value="envoyer"/>
    </form>
</body>
</html>
