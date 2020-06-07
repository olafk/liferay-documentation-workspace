
/**
 * Initialize audio player with json script, and trigger "play" immediately
 */
audioguide_initializeAndPlay = function(thePlayer, scripts) {

	var audioguide = scripts[0];
	var lastUpdate = 0;
	console.log("initializing audioguide script " + audioguide["name"] + " with " + audioguide["media"]);
	thePlayer.attr("src", audioguide["media"]);

	thePlayer.bind("timeupdate", function() {
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

			
		$.each(audioguide["command"], function(index, command) {
			var cmd       = command["command"];
			var timeStart = parseInt(command["timeStart"]);
			var timeEnd   = parseInt(command["timeEnd"]);
			var payload   = command["payload"];
			
			if(currentTime >= timeStart) {
				if(cmd == "scroll" && currentTime < timeStart+1) {
					
					console.log("firing " + cmd + " on time " + timeStart + " to element " + payload + " " + timeEnd );
					$(payload).each(function(e){this.scrollIntoView({block: "center", inline: "nearest", behavior:"smooth"});});
					
				} else if(cmd == "highlight"  && currentTime <= timeEnd) {
				
					console.log("firing " + cmd + " on time " + timeStart + " until " + timeEnd + " to " + payload);
					$(payload).addClass("replay-highlight");

					command["command"] = "unhighlight";
					
				} else if(cmd == "click" && currentTime < timeStart+1) {
					
					console.log("firing " + cmd + " on time " + timeStart + " to " + payload + "(" + $(payload).count + " elements)");
					$(payload).each(function(){this.click();});
					
				} else if(cmd == "eval" && currentTime < timeStart+1) {
					
					console.log("firing " + cmd + " on time " + timeStart + " to " + payload);
					eval(payload);
					
				}  
			}
			// un-highlight also on rewind of the media track
			if((currentTime < timeStart || currentTime > timeEnd) && cmd == "unhighlight") {
				
				console.log("firing " + cmd + " on time " + timeEnd + " to " + payload);
				$(payload).removeClass("replay-highlight");
				command["command"] = "highlight";
			}
		});
	});
	thePlayer.trigger("play");
};


audioguide_trigger = function() {
	var player = audioguide_findPlayer();
	
	if(player.prop("paused")) {
		player.trigger("play");
	} else {
		player.trigger("pause");
	}
};


audioguide_findPlayer = function() {
	var container = $("a[data-identifier=\"audioguide\"]");
	var player = $("audio[data-identifier=\"audioguide\"]");
	
	// Make sure that there is any configuration - when there's no
	// container, the ControlMenu Icon isn't there at all.
	if(container.length == 0) {
		console.log("no audioguide on this page");
		return;
	}

	if(player.length != 0){
//		console.log("found an existing audioguide player");
	} else {
		container.wrap("<span class='audioguidelink'></span>");
		container.before("<audio controls data-identifier=\"audioguide\" ></audio>");

		player = $("audio[data-identifier=\"audioguide\"]");

		var scriptUrl = container.data("audioguide-script");
		var audioUrl = container.data("audioguide-audio");
		if(scriptUrl != audioUrl) {
			// prevent playing json, in case we have an audio guide with script
			player.attr("src", audioUrl);
		}
		
		player.on("play", function(){
			container.toggleClass("audioguideplaying", true );
		});
		player.on("pause", function(){
			container.toggleClass("audioguideplaying", false);
		});


		$.getJSON(scriptUrl, "", function(data) {
			audioguide_initializeAndPlay(player, data);
		});
	}
	
	return player;
};

toast_trigger = function() {
//	console.log("triggering toast");
	var existingToast = $(".documentation-toast");
	if(existingToast.length == 0){
//		console.log("creating toast");
		var container = $("a[data-identifier=\"toast\"]");
		if(container.length == 0) {
			console.log("no toast on this page");
			return;
		}
		var toastUrl = container.data("documentation-toast");
		var toastDiv = "<div class='documentation-toast toast-hidden'>" 
					 + "<div><a href='javascript:toast_trigger()'>" + Liferay.Language.get("close")+ "</a></div>" 
					 + "<iframe src='" + toastUrl + "'></iframe>"
					 + "</div>"
		$("#main-content").append(toastDiv);
		setTimeout(function(){
			$(".documentation-toast").toggleClass("toast-hidden", false);
		}, 50);
		
	} else {
//		console.log("toggling existing toast visibility");
		existingToast.toggleClass("toast-hidden", ! existingToast.hasClass("toast-hidden"));
	}
	
};
