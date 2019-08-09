workflow "New workflow" {
  on = "push"
  resolves = ["Test"]
}

action "Test" {
  uses = "actions/setup-node@master"
  runs = "npm installs"
}
