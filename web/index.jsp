<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mageric.tools.Token" %>
<%
    String _token = Token.generateToken(8);
%>
<html>
<head>
    <meta charset="utf-8">
    <title>Polling...</title>
    <script type="text/javascript" src="/js/jquery-1.7.2.js"></script>
    <style type="text/css">
        #send {
            display: block;
            width: 98%;
            height: 23%;
            margin: 1%;
            float: left;
            text-align: left;
            border: 1px hidden #FFFFFF;
        }

        #send p {
            display: inline-block;
            width: 80px;
        }

        #send input[type="button"] {
            float: right;
            margin-top: 12px;
            margin-left: 14px;
            width: 150px;
            height: 39px;
            background: #0099ff;
            color: #FFF;
            border: none;
        }

        #user {
            display: inline-block;
            width: 220px;
        }

        #message {
            display: block;
            width: 100%;
        }

        #logs {
            display: block;
            width: 98%;
            margin: 1%;
            height: 70%;
            padding: 5px;
            border: 1px solid #0099ff;
        }
    </style>
    <script type="text/javascript">
        jQuery(function ($) {
            function processEvents(events) {
                if (events.length) {
                    /*            $('#logs').append('<span style="color: blue;">[client] ' + events.length + ' events</span><br/>');*/
                } else {
                    $('#logs').append('<span style="color: red;">[client] no event</span><br/>');
                }
                for (var i in events) {
                    $('#logs').append('<span>' + events[i] + '</span><br/>');
                }
            }

            function long_polling() {
                $.getJSON('ajax?user=<%=_token%>', function (events) {
                    processEvents(events);
                    long_polling();
                });
            }

            long_polling();
        });

        function send() {
            $.getJSON('send?user=' + $("#user").val() + "&message=" + $("#message").val(), function () {
                $("#message").val("");
            });
        }

        function clear() {
            $("#logs").html("");
        }
    </script>
</head>
<body>
<div id="logs" style="font-family: monospace;"></div>
<div id="send">
    <p>用户名:</p><input id="user" name="user" type="text" value="<%=_token%>"/>
    <textarea id="message" name="message" rows="3" cols="20"></textarea>
    <input type="button" value="CLEAR" onclick="clear()">
    <input type="button" value="SEND" onclick="send()">
</div>
</body>
</html>
