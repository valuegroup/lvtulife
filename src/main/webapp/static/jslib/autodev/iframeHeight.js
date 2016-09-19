
var FFextraHeight = 0;
if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
	FFextraHeight = 16;
}
function ReSizeiFrame(iframe) {
	if (iframe && !window.opera) {
		iframe.style.display = "block";
		if (iframe.contentDocument && iframe.contentDocument.body.offsetHeight) {
			iframe.height = iframe.contentDocument.body.offsetHeight + FFextraHeight;
		} else {
			if (iframe.Document && iframe.Document.body.scrollHeight) {
				iframe.height = iframe.Document.body.scrollHeight;
			}
		}
	}
}
function ReSizeiFrameJ(iframe, jHeight) {
	if (iframe && !window.opera) {
		iframe.style.display = "block";
		if (iframe.contentDocument && iframe.contentDocument.body.offsetHeight) {
			iframe.height = iframe.contentDocument.body.offsetHeight + FFextraHeight + jHeight;
		} else {
			if (iframe.Document && iframe.Document.body.scrollHeight) {
				iframe.height = iframe.Document.body.scrollHeight + jHeight;
			}
		}
	}
}

