<?php
    require_once('TwitterAPIExchange.php');
        include 'dbConnection.php';

        //echo "Hello World";

        /** Set access tokens here - see: https://dev.twitter.com/apps/ **/
        /*$settings = array(
            'oauth_access_token' => "449929980-THahpBVoO6V7pSRdNRzdQgVxDbTku2EYUV4UE1eG",
            'oauth_access_token_secret' => "rPm2LSvJZb5RtRGVo7A0X9npsPx8OnXg9PDVxQ3Ql0XJV",
            'consumer_key' => "x6rBKStbG8ArzEv6THQkUHdh3",
            'consumer_secret' => "KKjlvaR3l2ivvCVRXIb1t63nQRpGCJ9gvR5gl1Yk43yCjTLzu8"
        );*/

        $settings = array(
            'oauth_access_token' => "4820451316-GZXT8Z2SNUCkH4HFAb6EDjxsDsLUCpp576u6xmb",
            'oauth_access_token_secret' => "d3Cesg7gsrr7xJajgFo7F0d0V2zK07jhtk9TsNpj90iXQ",
            'consumer_key' => "CeOElIwBtkG2OTVzdzsQ4GJYf",
            'consumer_secret' => "3TIm1woXAdiDgpMeQxORwlzmMk9IOFTmL8Tx7jSFja4mDUmf9v"
        );
        $twitter = new TwitterAPIExchange($settings);


        $maxID = 123456789101112121212;
        $url = 'https://api.twitter.com/1.1/search/tweets.json';
        $getfield = '?max_id=731145679288983551&q=%23drunk&lang=en&count=100&include_entities=1';//'?q=%23drunk&lang=eu&count=100';
        $requestMethod = 'GET';
        
        $json = $twitter->setGetfield($getfield)->buildOauth($url, $requestMethod)->performRequest(); 

        var_dump($json);
        echo "\n json decode geliyor \n";

        $result = json_decode($json);

        echo "\n war dump gelioyr\n";

        var_dump($result);

        echo "\n for each gelioyr\n";

        $tweets = $result->statuses;

        $size = count($tweets);

        for($i=0; $i< $size; $i++){
            $currentTweet = $tweets[$i];
            echo $currentTweet->id." : ".$conn->real_escape_string($currentTweet->text) ."<br>";

        }

?>