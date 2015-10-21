<!DOCTYPE html>
<html lang="en">
<head>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Two grids demo</title>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="../dist/gridstack.css"/>
    <link rel="stylesheet" href="../dist/gridstack-extra.css"/>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.0/jquery-ui.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/3.5.0/lodash.min.js"></script>
    <script src="../dist/gridstack.js"></script>

    <style type="text/css">
        #grid1 {
            background: lightgoldenrodyellow;
        }

        #grid2 {
            background: lightcyan;
        }

        .grid-stack-item-content {
            color: #2c3e50;
            text-align: center;
            background-color: #18bc9c;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <h1>Two grids demo</h1>

    <div class="row">
        <div class="col-md-6">
            <div class="grid-stack grid-stack-6" id="grid1">
            </div>
        </div>
        <div class="col-md-6">
            <div class="grid-stack grid-stack-6" id="grid2">
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(function () {
        var options = {
            width: 6,
            float: true
        };
        $('#grid1').gridstack(options);
        $('#grid2').gridstack(options);

        var items = [
            {x: 0, y: 0, width: 2, height: 2},
            {x: 3, y: 1, width: 1, height: 2},
            {x: 4, y: 1, width: 1, height: 1},
            {x: 2, y: 3, width: 3, height: 1},
            {x: 2, y: 5, width: 1, height: 1}
        ];

        $('.grid-stack').each(function () {
            var grid = $(this).data('gridstack');

            _.each(items, function (node) {
                grid.add_widget($('<div><div class="grid-stack-item-content" /><div/>'),
                        node.x, node.y, node.width, node.height);
            }, this);
        });
    });
</script>
</body>
</html>
