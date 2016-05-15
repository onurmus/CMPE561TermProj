<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta charset="utf-8">
	<title>Tweet getirrr</title>
</head>
<body>
	<?php
		require_once('TwitterAPIExchange.php');
		include 'dbConnection.php';


	$whereWeLeft = 182;
	$count  = 0;

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

		echo "The time is " . date("h:i:sa") ."<br>";


		$sql1 = "SELECT distinct(userId) FROM `dataset3` WHERE hashTag in ('drunk','drank','imdrunk') order by userId";
		$result = $conn->query($sql1);

		$url = 'https://api.twitter.com/1.1/statuses/user_timeline.json';
		$requestMethod = 'GET';

		$maxID = "1234567891213141516178";

		if ($result->num_rows > 0) {
		    // output data of each row

			

		    while($row = $result->fetch_assoc()) {

		    	$count++;

		    	if($count<$whereWeLeft){
		    		continue;
		    	}

		        echo "id: " . $row["userId"]. "<br>";

		        $currentUserId = $row["userId"];


		        $getfield = '?lang=en&count=100&user_id='.number_format($currentUserId,0,'','');//.'&max_id='.number_format($maxID,0,'','');

		        echo "get field : ". $getfield. " counter" .$count." <br>";

		        $json = $twitter->setGetfield($getfield)->buildOauth($url, $requestMethod)->performRequest();

		        $callResult = json_decode($json);

		        //echo $json;

				//var_dump($callResult);

				/*if($count == 0){
					break;
				}*/

				


				if(isset($callResult->errors)){
					
					if($callResult->errors[0]->code == 34){
						echo  $callResult->errors[0]->message ."<br>";
						continue;
					}
					var_dump($callResult);
					echo "count is : ".$count. "<br>";
				 	die("twitterdan hata geldi güzelim");
				}

				$tweets = $callResult;

				$size = count($tweets);

				if($size == 0 ){
					echo "tweets size 0 geldi <br>";
				}

				for ($k=0; $k < $size; $k++) { 
					$currentTweet = $tweets[$k];

					$sql = "INSERT INTO `dataset3v3`(`userId`, `tweetId`, `hashtag`, `tweet`)  
					VALUES ('".$currentUserId."', '".$currentTweet->id_str."', ' ','".$conn->real_escape_string($currentTweet->text)."')";

					if (!($conn->query($sql) === TRUE)) {
					   echo "Error: " . $sql . "<br>" . $conn->error."<br>";
					}

				}

				

		    }

		    echo "count is : ".$count. "<br>";

		} else {
		    echo "0 results";
		}


		$conn->close();
	          
	?>
</body>
</html>