for name in $( find -name *.tex )
do
  sed 's,\\cite{,\\citep{,g' <$name >dfasdfas.tex
  mv dfasdfas.tex $name
done
