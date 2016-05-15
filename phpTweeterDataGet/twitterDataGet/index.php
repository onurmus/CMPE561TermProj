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

		//echo "Hello World";

		/** Set access tokens here - see: https://dev.twitter.com/apps/ **/
		$settings = array(
		    'oauth_access_token' => "449929980-THahpBVoO6V7pSRdNRzdQgVxDbTku2EYUV4UE1eG",
		    'oauth_access_token_secret' => "rPm2LSvJZb5RtRGVo7A0X9npsPx8OnXg9PDVxQ3Ql0XJV",
		    'consumer_key' => "x6rBKStbG8ArzEv6THQkUHdh3",
		    'consumer_secret' => "KKjlvaR3l2ivvCVRXIb1t63nQRpGCJ9gvR5gl1Yk43yCjTLzu8"
		);

		/*$settings = array(
            'oauth_access_token' => "4820451316-GZXT8Z2SNUCkH4HFAb6EDjxsDsLUCpp576u6xmb",
            'oauth_access_token_secret' => "d3Cesg7gsrr7xJajgFo7F0d0V2zK07jhtk9TsNpj90iXQ",
            'consumer_key' => "CeOElIwBtkG2OTVzdzsQ4GJYf",
            'consumer_secret' => "3TIm1woXAdiDgpMeQxORwlzmMk9IOFTmL8Tx7jSFja4mDUmf9v"
        );*/
		$twitter = new TwitterAPIExchange($settings);

		

		echo "The time is " . date("h:i:sa") ."<br>";


		
		for ($k=5; $k > -1; $k--) { 
			switch ($k) {
				case 0:
					$currentTag = "drank";
					break;
				case 1:
					$currentTag = "drunk";
					break;
				case 2:
					$currentTag = "imdrunk";
					break;
				case 3:
					$currentTag = "notdrunk";
					break;
				case 4:
					$currentTag = "imnotdrunk";
					break;
				case 5:
					$currentTag = "sober";
					break;			
				default:
					die("k yi eşleşiremedi");
					break;
			}

			$maxID = "1234567891213141516178"; //"1234567891213141516178";
			$getfield = '?q=%23'.$currentTag.'&lang=en&count=100&max_id='.number_format($maxID,0,'','');

			$reqCounter = 1;

			while($reqCounter < 75){
				try{
					$url = 'https://api.twitter.com/1.1/search/tweets.json';
					//'?q=%23drunk&lang=eu&count=100';
					$requestMethod = 'GET';

					echo "getfield : ".$getfield."<br>";
					
					$json = $twitter->setGetfield($getfield)->buildOauth($url, $requestMethod)->performRequest(); 

					echo "request : ".$reqCounter."<br> ";
					$reqCounter++;

					$result = json_decode($json);

					var_dump($json);

					if(isset($result->errors)){
						echo "maxID ". $maxID ."<br>";
					 	die("twitterdan hata geldi güzelim");
					}

					$tweets = $result->statuses;

					if(isset($result->search_metadata->next_results)){
						$getfield = $result->search_metadata->next_results;

						$getfield = str_replace('%2523','%23',$getfield);

					}else{
						$getfield = '?q=%23'.$currentTag.'&lang=en&count=100&max_id='.number_format($maxID,0,'','');
					}

					
					
					$size = count($tweets);

					if($size == 0){
						echo "maxID ". $maxID ."<br>";
					 	echo "sonuç dönmedi array size 0 " . $currentTag."<br>";
					 	break;
					}

					$tempMax = $maxID;

					for($i=0; $i< $size; $i++){
						$currentTweet = $tweets[$i];
						//echo $currentTweet->id." : ".$conn->real_escape_string($currentTweet->text) ."<br>";


						$sql = "INSERT INTO `tbltweets`(`userId`, `tweetId`, `hashtag`, `tweet`)  
						VALUES ('".$currentTweet->user->id_str."', '".$currentTweet->id_str."', '".$currentTag."','".$conn->real_escape_string($currentTweet->text)."')";

						//echo "<br>".$sql."<br>";

						if (!($conn->query($sql) === TRUE)) {
						   echo "Error: " . $sql . "<br>" . $conn->error."<br>";
						}

						if($i == $size-1){				
								$maxID = $currentTweet->id_str;
						}

						
					}	

					if(number_format((float)$maxID,0,'','') >=  number_format((float)$tempMax,0,'','')){
							echo "max küçülmüyor <br>";
							break;
						}
				}catch(Exception $e) {
					echo "maxID ". $maxID."<br>";
				 	echo 'Message: ' .$e->getMessage();
				 	break;
				}


			}
		}
		echo $maxID;
		$conn->close();
	          
	?>
</body>
</html>

