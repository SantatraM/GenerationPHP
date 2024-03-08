<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="<?php echo site_url("voitureControlleur/insertvoiture"); ?>" method="post">
               <input name='designation' type='text' placeholder='designation' >
        <select> 
    <?php foreach($listemarque as $marques) { ?>

        <option name="marque" value="<?php echo $marques->idmarque; ?>">
            <?php echo $marques->nommarque; ?>
        </option>
    <?php } ?>

        </select>
        <input name='numero' type='number' placeholder='numero' >
 
    </form>
</body>
</html>
