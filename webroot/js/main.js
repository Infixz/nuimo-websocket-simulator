(function() {

	$.ajax({
		url: "/api?webSocketAddress"
	}).done(function (address){
		$("#webSocketAddress").text("WebSocket : "+address);
	})

	var predefinedEvents = ["ButtonPress",
							"RotateLeft",
							"RotateRight",
							"TouchLeft",
							"TouchRight",
							"TouchUp",
							"TouchDown",
							"TouchMiddle",
							"TouchRelease",
							"SwipeLeft",
							"SwipeRight",
							"SwipeUp",
							"SwipeDown",
							"FlyLeft",
							"FlyRight",
							"FlyTowards",
							"FlyAway",
							"FlyUp",
							"FlyDown",
							"Program1",
							"Program2",
							"Program3",
							"Program4",
							"Program5",
							"Program6"]

	var html = ""
	for (var i = 0; i < predefinedEvents.length; i++) {
		var predefinedEvent = predefinedEvents[i];
		html += '<a class="button expand predefinedEvent">'+predefinedEvent+'</a>'
	};
	$("#predefinedEventButtons").html(html)

	var appendHistory = function(text){
		var history = $("#history").text()
		history += "\n"+text
		$("#history").text(history)
	}
	var sendEvent = function(text){
		
		appendHistory("Send : "+text)

		$.ajax({
			url: "/api?send="+text
		}).done(function (address){
			appendHistory("OK : "+text)
		})
	}

	$('.predefinedEvent').click(function(e){
		var text = e.target.text
		sendEvent(text)
	})

	setTimeout(function() {
		var height = $(document).height() / 2
		$("#historyRow").css("height", height)
		$("#history").css("height", height)

		$('form').submit(function() {
			var text = $("#customEvent").val()
			$("#customEvent").val("")
			sendEvent(text)
			return false;
		});		
	}, 0);

})()
