<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>测试AnnotationConfig</title>

    <script src="static/jquery-1.10.2.js"></script>
    <script>
        window.onload = function() {
            var saveDataAry = [];
            var data1 = {
                "id" : "2",
                "name" : "gz"
            };
            var data2 = {
                "id" : "3",
                "name" : "gr"
            };
            saveDataAry.push(data1);
            saveDataAry.push(data2);
            $.ajax({
                type : "POST",
                url : "showcity",
                dataType : "json",
                data : {city:JSON.stringify(saveDataAry)},
                success : function(data) {

                }
            });



            var data1 = {
                "id" : "2",
                "name" : "gz"
            };

            $.ajax({
                type : "POST",
                url : "getcity",
                dataType : "json",
                data : {city:JSON.stringify(data1), a: '20'},
                success : function(data) {

                }
            });
        }
    </script>

</head>

<body>

    <h1>
        这是我得AnnotationConfig页面
    </h1>

</body>
</html>