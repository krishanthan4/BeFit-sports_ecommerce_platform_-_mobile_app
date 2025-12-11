<?php 

$router = include "routes.php";
include "common.php";



$uri = parse_url($_SERVER["REQUEST_URI"])["path"];

function abort($value=404){
http_response_code($value);
return require_once("./guis/status-codes/{$value}.php");
}

function routeToUrl($uri,$router){

if(array_key_exists($uri,$router)){
require_once($router[$uri]);
}else{
    abort();
}

}

routeToUrl($uri,$router);

?>