isDebug = true
icon = () -> new OpenLayers.Icon "http://www.openstreetmap.org/openlayers/img/marker.png", {w:21, h:25}
makePoint = (lng, lat) -> (new OpenLayers.LonLat lng, lat).transform new OpenLayers.Projection("EPSG:4326"), do map.getProjectionObject
currentPopup = null

map = new OpenLayers.Map "map", {displayProjection: new OpenLayers.Projection "EPSG:4326", units: "m", numZoomLevels: 19}
map.addControl new OpenLayers.Control.DragPan
map.addLayer new OpenLayers.Layer.OSM
lyrMarkers = new OpenLayers.Layer.Markers "Markers"
map.addLayer lyrMarkers
map.setCenter (makePoint -3.65, 40.4), 10 #center and zoom, Spanish defaults

addMarker = (lng, lat, info) ->
  feature = new OpenLayers.Feature lyrMarkers, (makePoint lng, lat), {popupContentHTML: info, overflow: "auto"}
  feature.closeBox = true
  marker = new OpenLayers.Marker (makePoint lng, lat), do icon
  lyrMarkers.addMarker marker

  marker.events.register "mousedown", feature, (evt) ->
    do currentPopup.hide if currentPopup?
    if not @popup?
      @popup = @createPopup @closeBox
      map.addPopup @popup
    do @popup.show
    currentPopup = @popup
    OpenLayers.Event.stop(evt)  

$.ajax
  dataType: "json"
  type: "POST"
  url: "http://dbpedia.org/sparql"
  data:
    query: "SELECT * WHERE {
    	?uri geo:lat ?lat .
    	?uri geo:long ?lon .
    	?uri rdf:type ?thetype .
    	FILTER (
    	  (?lat> 40.25  && ?lat < 40.75) &&
    	  (?lon> -4.0  && ?lon < -3.5) &&
          regex(?thetype,'^http://schema.org')
        )
      }"
  success: (data) ->
  	console.log(data) if isDebug
  	addMarker b.lon.value, b.lat.value, (b.uri.value.concat " \"", b.thetype.value, "\"") for b in data.results.bindings
