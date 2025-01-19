terraform {
  required_providers {
    virtualbox = {
      source = "terra-farm/virtualbox"
      version = "0.2.2-alpha.1"
    }
  }
}

provider "virtualbox" {
  # Configuration options
}

resource "virtualbox_vm" "ansible_controller" {
  name      = "ansible-controller"
  image     = "https://cloud-images.ubuntu.com/releases/22.04/release/ubuntu-22.04-server-cloudimg-amd64.ova"
  cpus      = var.vm_cpus
  memory    = var.vm_memory
  
  network_adapter {
    type           = "bridged"
    host_interface = "Default Switch"
  }

  boot_wait = "10s"
  
  guest_additions_mode = "disable"

  provisioner "remote-exec" {
    inline = [
      "sudo apt-get update",
      "sudo apt-get install -y ansible python3-pip",
      "ansible --version"
    ]

    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file("~/.ssh/id_rsa")
      host        = self.network_adapter[0].ipv4_address
    }
  }
}
