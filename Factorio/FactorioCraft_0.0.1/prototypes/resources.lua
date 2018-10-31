mod_name = "__FactorioCraft__"

local function autoplace_settings(name, coverage)
  local ret = {
    control = name,
    sharpness = 1,
    richness_multiplier = 1500,
    richness_multiplier_distance_bonus = 30,
    richness_base = 500,
    coverage = coverage,
    peaks = {
      {
        noise_layer = name,
        noise_octaves_difference = -1.5,
        noise_persistence = 0.3,
      },
    }
  }
  for i, resource in ipairs({"redstone-ore"}) do
    if resource ~= name then
      ret.starting_area_size = 600 * coverage
      ret.starting_area_amount = 1500
    end
  end
  return ret
end

local function resource(name, map_color, hardness, coverage)
  if hardness == nil then hardness = 0.9 end
  if coverage == nil then coverage = 0.02 end
  return {
    type = "resource",
    name = name,
    icon = mod_name.."/graphics/icons/" .. name .. ".png",
    icon_size = 32,
    flags = {"placeable-neutral"},
    order="a-b-a",
    minable =
    {
      hardness = hardness,
      --mining_particle = name .. "-particle",
      mining_time = 2,
      result = name
    },
    collision_box = {{ -0.1, -0.1}, {0.1, 0.1}},
    selection_box = {{ -0.5, -0.5}, {0.5, 0.5}},
    autoplace = autoplace_settings(name, coverage),
    stage_counts = {5000, 3000, 1500, 800, 400, 100, 50, 10},
    stages =
    {
      sheet =
      {
        filename = mod_name.."/graphics/entity/" .. name .. ".png",
        priority = "extra-high",
        width = 64,
        height = 64,
        frame_count = 8,
        variation_count = 8,
        hr_version =
          {
          filename = mod_name.."/graphics/entity/" .. "hr-" .. name .. ".png",
          priority = "extra-high",
          width = 128,
          height = 128,
          frame_count = 8,
          variation_count = 8,
          scale = 0.5
          }
      }
    },
    map_color = map_color
  }
end

data:extend(
{
  resource("redstone-ore", {r=0.9, g=0.1, b=0.1})
}
)
