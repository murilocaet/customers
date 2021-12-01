
output "customers-ip" {
  value = "${aws_instance.customers-vm.public_ip}"
}
output "customers-dns" {
  value = "${aws_instance.customers-vm.public_dns}"
}