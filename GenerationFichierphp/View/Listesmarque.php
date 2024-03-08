<!DOCTYPE html>
<html>
<body>
<table>
    <tr>
        <td>idmarque</td><td>nommarque</td><td>datesortie</td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <?php foreach($listemarque as $marques) { ?>
            <td><?= $marques->idmarque; ?></td><td><?= $marques->nommarque; ?></td><td><?= $marques->datesortie; ?></td>
            <td>
                <a href="<?php echo site_url("marqueControlleur/FormulaireUpdatemarque?idmarque=").$marques->idmarque; ?>">Modifier</a>
            </td>
            <td>
                <a href="<?php echo site_url("marqueControlleur/deletemarque?idmarque=").$marques->idmarque; ?>">Supprimer</a>
            </td>
        <?php } ?>
    </tr>
</table>
</body>
</html>
