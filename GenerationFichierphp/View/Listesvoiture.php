<!DOCTYPE html>
<html>
<body>
<table>
    <tr>
        <td>idvoiture</td><td>designation</td><td>idmarque</td><td>numero</td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <?php foreach($listevoiture as $voitures) { ?>
            <td><?= $voitures->idvoiture; ?></td><td><?= $voitures->designation; ?></td><td><?= $voitures->idmarque; ?></td><td><?= $voitures->numero; ?></td>
            <td>
                <a href="<?php echo site_url("voitureControlleur/FormulaireUpdatevoiture?idvoiture=").$voitures->idvoiture; ?>">Modifier</a>
            </td>
            <td>
                <a href="<?php echo site_url("voitureControlleur/deletevoiture?idvoiture=").$voitures->idvoiture; ?>">Supprimer</a>
            </td>
        <?php } ?>
    </tr>
</table>
</body>
</html>
