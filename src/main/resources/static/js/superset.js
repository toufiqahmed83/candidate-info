///*import { embedDashboard } from "@preset-sdk/embedded";
var sSetUrl="http://192.168.0.133:8089";
//$(function () {
//
////embedDashboard({
////    id: "ca622c12-86cf-43a8-8182-2a12f0f1386b", // from the Embedded dialog
////    supersetDomain: sSetUrl, // from the Embedded dialog
////    mountPoint: document.getElementById("my-superset-container"), // any HTML element that can contain an iframe
////    fetchGuestToken: () => fetchGuestTokenFromBackend(), // function responsible to return a guest_token
////    dashboardUiConfig: {}, // dashboard UI configuration. Options: hideTitle, hideChartControls, filters.expanded, urlParams (all optional)
////});
//
//// $(document).ready(function () {
////     auth();
////    });
//
//});

function auth()
{
fetch(sSetUrl+'/api/v1/security/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    username: 'gbf-user',
    password: '123456'
  })
})
.then(response => response.json())
.then(data => console.log('Token:', data.access_token));
}