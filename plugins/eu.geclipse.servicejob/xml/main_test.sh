
for i in `ls test*.sh` ; do

function runTest() {
echo notimplemented
return -1
}

function getName() {
echo notimplemented
return -1
}

function getStatus() {
echo notimplemented
return -1
}


. ./$i

echo "<root>"
echo "<NAME>"
 getName
echo "</NAME>"
echo "<DETAILS>"
 runTest
echo "</DETAILS>"
STATUS_TEXT=`getStatus`
STATUS_CODE=$?
echo "<STATUS code=\"$STATUS_CODE\">"
echo $STATUS_TEXT
echo "</STATUS>"
echo "</root>"

done
