
/**
 * Initialize audio player with json script, and trigger "play" immediately
 */
audioguide_initializeAndPlay = function(thePlayer, scripts) {

	var audioguide = scripts[0];
	var lastUpdate = -1;
	console.log("initializing audioguide script " + audioguide["name"] + " with " + audioguide["media"]);
	thePlayer.src = audioguide["media"];

	thePlayer.ontimeupdate = function() {
		// run max once per second to avoid firing twice.
		// events come in multiple times per second.
		// On slower environments, we might miss a beat - 
		// is that an issue?
		var player = this;
		var currentTime = player.currentTime;
				
		if(parseInt(lastUpdate) == parseInt(currentTime)) {
			return;
		}
		lastUpdate = parseInt(currentTime);

			
		audioguide["command"].forEach(function(command) {
			var cmd       = command["command"];
			var timeStart = command["timeStart"];
			var timeEnd   = command["timeEnd"];
			var payload   = command["payload"];
			
			if(currentTime >= timeStart) {
				if(cmd == "scroll" && currentTime < timeStart+1) {
					
					console.log("firing " + cmd + " on time " + timeStart + " to element " + payload + " " + timeEnd );
					document.querySelectorAll(payload).forEach(function(e){this.scrollIntoView();});
					
				} else if(cmd == "highlight"  && currentTime <= timeEnd) {
				
					console.log("firing " + cmd + " on time " + timeStart + " until " + timeEnd + " to " + payload);
					document.querySelectorAll(payload).forEach(function(e){e.classList.add("replay-highlight")});

					command["command"] = "unhighlight";
					
				} else if(cmd == "click" && currentTime < timeStart+1) {
					var targetElements = document.querySelectorAll(payload);
					console.log("firing " + cmd + " on time " + timeStart + " to " + payload + "(" + targetElements.count + " elements)");
					targetElements.forEach(function(elem){elem.click();});
					
				} else if(cmd == "eval" && currentTime < timeStart+1) {
					
					console.log("firing " + cmd + " on time " + timeStart + " to " + payload);
					eval(payload);
					
				}  
			}
			// un-highlight also on rewind of the media track
			if((currentTime < timeStart || currentTime > timeEnd) && cmd == "unhighlight") {
				
				console.log("firing " + cmd + " on time " + timeEnd + " to " + payload);
				document.querySelectorAll(payload).forEach(function(e){e.classList.remove("replay-highlight")});
				command["command"] = "highlight";
			}
		});
	};
	thePlayer.play();
};


audioguide_trigger = function() {
	var player = audioguide_findPlayer();
	
	if(player.paused) {
		player.play();
	} else {
		player.pause();
	}
};


audioguide_findPlayer = function() {
	var container = document.querySelector("a[data-identifier=\"audioguide\"]");
	var player = document.querySelector("audio[data-identifier=\"audioguide\"]");
	
	// Make sure that there is any configuration - when there's no
	// container, the ControlMenu Icon isn't there at all.
	if(container == null) {
		console.log("no audioguide on this page");
		return;
	}

	if(player != null){
//		console.log("found an existing audioguide player");
	} else {
		originalHtml = container.innerHTML;
		container.innerHTML = "<audio controls data-identifier=\"audioguide\" ></audio><span class='audioguidelink'>" + originalHtml + "</span>";

		player = document.querySelector("audio[data-identifier=\"audioguide\"]");

//  !!!!!!!!!!!!!!!!!!
		var scriptUrl = container.dataset.audioguidescript;
		var audioUrl = container.dataset.audioguideaudio;
		if(scriptUrl != audioUrl) {
			// prevent playing json, in case we have an audio guide with script
			player.src = audioUrl;
		}
		
		player.onplay = function(){
			container.classList.add("audioguideplaying");
		};
		player.onpause = function(){
			container.classList.remove("audioguideplaying");
		};
	
        var http_request = new XMLHttpRequest();

        http_request.onreadystatechange = function() {
           if (http_request.readyState == 4  ) {
              var data = JSON.parse(http_request.responseText);
              audioguide_initializeAndPlay(player, data);
           }
        }
		
        http_request.open("GET", scriptUrl, true);
        http_request.send();
	}
	
	return player;
};


toast_trigger = function() {
//	console.log("triggering toast");
	var existingToast = document.querySelector(".documentation-toast");
	if(existingToast == null){
//		console.log("creating toast");
		var container = document.querySelector("a[data-identifier=\"toast\"]");
		if(container == null) {
			console.log("no toast on this page");
			return;
		}
		var toastUrl = container.dataset.documentationtoast;
		var toastDiv = "<div class='documentation-toast toast-hidden'>" 
					 + "<div><a href='javascript:toast_trigger()'>" + Liferay.Language.get("close")+ "</a></div>" 
					 + "<iframe src='" + toastUrl + "'></iframe>"
					 + "</div>"
		document.getElementById("main-content").insertAdjacentHTML('beforeend',toastDiv);
		setTimeout(function(){
			document.querySelector(".documentation-toast").classList.remove("toast-hidden");
		}, 50);
		
	} else {
//		console.log("toggling existing toast visibility");
		existingToast.classList.toggle("toast-hidden");
	}
	
};


generate_controlpanel_documentation = function(primary, secondary) {
	var headline = document.querySelector("span[data-qa-id=\"headerTitle\"]").innerHTML;
	var xmlhttp=new XMLHttpRequest();
	    
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200) {
	        console.log(xmlhttp.responseText);
	  }
	}
	    
	xmlhttp.open("GET",'http://localhost:8080/o/cpdocauthor?primary=' + primary + '&secondary=' + secondary + '&headline=' + headline ,true);
	xmlhttp.send();
};

