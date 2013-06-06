data =
	languages:
		java: ['.class', '*.jar']
		latex: ['*.aux', '*.bbl', '*.blg', '*.brf', '*.cut', '*.lof', '*.toc', '*.log', '*.lol', '*.lot', '*.out', '*.pdf']
		python: ['*.pyc']
	tools:
		archivers: ['*.7z', '*.dmg', '*.gz', '*.iso', '*.jar', '*.rar', '*.tar', '*.zip']
		eclipse: ['.classpath', '.project', '.settings/']
		protege: ['catalog-v001.xml']
		subversion: ['.svn/']
		texniccenter: ['*.tcp', '*.tps']
		
for category of data
	$('#selectors').append "<div id='#{category}'><b>#{category}: </b></div>"
	$('#results').append "<div id='r#{category}' class='category'><pre># #{category}</pre></div>"
	for technology of data[category]
		$('#' + category).append "<span id='#{technology}' class='option'>#{technology}</span>"
		lines = data[category][technology].reduce (x, y) -> "#{x}\n" + y
		$('#r' + category).append "<pre id='#{'r' + technology}' class='hidden result'># #{technology}\n#{lines}</pre>"

toggleOption = () ->
	option = $(@)
	res = $('#r' + option.attr 'id')
	if option.hasClass 'selected'
		option.removeClass 'selected'
		res.addClass 'hidden'
	else
		option.addClass 'selected'
		res.removeClass 'hidden'
		 
$(() -> $('.option').click toggleOption)