<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="<?php echo site_url("voitureController/updatevoiture") ?>" method="post">
               <input name='idvoiture' type='text' value='<?php echo $voiture->idvoiture; ?>' hidden>
       <input name='designation' type='text' value='<?php echo $voiture->designation; ?>'>
       <select> 
    <?php foreach($listemarque as $marques) { ?>

        <option name="marque" value="<?php echo $marques->idmarque; ?>">
            <?php echo $marques->nommarque; ?>
        </option>
    <?php } ?>

        </select>
       <input name='numero' type='number' value='<?php echo $voiture->numero; ?>'>

        <input type="submit" value="envoyer"/>
    </form>
</body>
</html>
