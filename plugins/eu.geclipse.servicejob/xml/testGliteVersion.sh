WARN_VERSION=3.2.0
NAME=Glite_version

ERROR=1
WARNING=2

STATUS=0

function getName() {
echo $NAME
}

function getStatus(){
if [ $(( $STATUS & $ERROR )) == 1 ]; then
 echo ERROR
 return 2
else
 if [ $(( $STATUS & $WARNING )) == 1 ]; then
  echo WARNING
  return 1
 else
  echo OK
  return 0
 fi
fi
}

function runTest(){
VERSION=`rpm -q glite-version --queryformat '%{VERSION}'`
 if [ $VERSION \> $REQ_VERSION ]; then
  echo "OK: Glite is in version $VERSION"
 else
  if [ $VERSION == $REQ_VERSION ]; then
   echo "OK: Glite is in version $VERSION"
  else
   STATUS=$(( $STATUS | $ERROR ))
   echo "ERROR: Glite is in version $VERSION ( $REQ_VERSION required) "
  fi
 fi

}

