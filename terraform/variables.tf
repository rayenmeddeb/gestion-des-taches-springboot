variable "vm_cpus" {
  description = "Number of CPUs for the Ansible controller VM"
  type        = number
  default     = 2
}

variable "vm_memory" {
  description = "Amount of memory in MB for the Ansible controller VM"
  type        = number
  default     = 4096
}
