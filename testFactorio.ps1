Add-Type -assembly "system.io.compression.filesystem"
$destination = $env:APPDATA + "\Factorio\Mods\FactorioCraft_0.0.1.zip"
If (Test-Path $destination) { Remove-Item $destination }
[io.compression.zipfile]::CreateFromDirectory(".\Factorio", $destination) 